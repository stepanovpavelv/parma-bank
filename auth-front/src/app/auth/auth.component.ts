import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { takeUntil } from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Subject } from 'rxjs/internal/Subject';
import { OpenIdTokenResponse } from './token-response';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  private readonly ngUnsubscribe$: Subject<number>;

  constructor(private readonly authService: AuthService, 
              private activatedRoute: ActivatedRoute,
              private router: Router) {
      this.ngUnsubscribe$ = new Subject();    
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((params: any) => {
      if (params?.['code']) {
        let code: string = params['code'];
        this.authService.getToken(code).pipe(
          takeUntil(this.ngUnsubscribe$)
        ).subscribe((tokenResponse: OpenIdTokenResponse) => {
             if (tokenResponse.id_token) {
              sessionStorage.setItem('id_token', tokenResponse.id_token);
              sessionStorage.setItem('refresh_token', tokenResponse.refresh_token);
              sessionStorage.setItem('access_token', tokenResponse.access_token);
              this.router.navigate(['/main']);
             }
        });
        console.log('code === ', code);
      }
    });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe$.next(0);
    this.ngUnsubscribe$.complete();
  }
}