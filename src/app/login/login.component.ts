import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  constructor(private sharedService: SharedService) {}

  ngOnInit(): void { 
  }
}
