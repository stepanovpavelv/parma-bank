import { Component, OnInit } from '@angular/core';
import logoutUrl from '../constants/logout';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  idTokenHint: string | null = '';
  accessTokenHint: string | null = '';

  constructor() { }

  ngOnInit(): void {
    this.idTokenHint = sessionStorage.getItem('id_token');
    this.accessTokenHint = sessionStorage.getItem('access_token');
  }

  logout() : void {
    let token: string | null = sessionStorage.getItem('id_token');
    if (!token) {
      throw Error('Пользователь уже вышел.');
    }
    sessionStorage.removeItem('id_token');
    sessionStorage.removeItem('refresh_token');
    sessionStorage.removeItem('access_token');
    
    window.location.href = logoutUrl(token);
  }
}