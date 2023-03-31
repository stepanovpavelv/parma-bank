import { Component, OnInit } from '@angular/core';
import { UrlService } from '../services/url.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private readonly urlServ: UrlService) { }

  ngOnInit(): void {
  }

  async redirect() : Promise<void> {
    let redirectUrl: string = await this.urlServ.getLoginUrl();
    window.location.href = redirectUrl;
  }
}