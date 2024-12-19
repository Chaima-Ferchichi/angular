import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-admin-dash',
  templateUrl: './admin-dash.component.html',
  styleUrl: './admin-dash.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AdminDashComponent implements OnInit {

  demandes:any;
  nombreDemandes: number = 0;  // Nouvelle variable pour stocker le nombre de demandes

  constructor (public _shared:SharedService,private router:Router){
  }

  ngOnInit(): void {
    this._shared.getHebergement().subscribe(
      res=>{
        console.log(res);
        this.demandes=res;
        this.nombreDemandes = this.demandes.length;  // Assigner le nombre de demandes
        
      },
      err=>{
        console.log(err);
      }
     );
  }

  delete(id:any){
    this._shared.refuserDemande(id).subscribe(
      res=>{
          console.log(res);
          this.ngOnInit();
      },
      err=>{
        console.log(err);
      }
    );
  }

  Accepter(id:any){

    this._shared.accepterDemande(id).subscribe(
      res=>{
          console.log(res);
          this.ngOnInit();
      },
      err =>{
         console.log(err);
      }
    );
  }
}
