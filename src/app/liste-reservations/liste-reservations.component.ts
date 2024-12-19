import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-liste-reservations',
  templateUrl: './liste-reservations.component.html',
  styleUrl: './liste-reservations.component.css',
  encapsulation: ViewEncapsulation.None
})
export class ListeReservationsComponent implements OnInit {

  reservations: any[] = [];
  nombrereservations: number = 0;
  idProp:any; // Nouvelle variable pour stocker le nombre de demandes
  client:any;
  hebergement:any;
  

  constructor (public _shared:SharedService,private router:Router){
  }
 
  ngOnInit(): void {
    this.idProp = 3;

    this._shared.getReservationByProprietaireId(this.idProp).subscribe(
        res => {
            console.log(res);
            this.reservations = res;

            // Récupérer les coordonnées des clients et les détails des chambres
            this.reservations.forEach((reservation: any) => {
                // Ajouter les informations du client
                this._shared.getClientbyid(reservation.clientId).subscribe(
                    clientData => {
                        reservation.client = clientData; // Ajouter les infos du client
                    },
                    err => {
                        console.error('Erreur lors de la récupération du client :', err);
                    }
                );

                // Ajouter les détails des chambres
                reservation.chambresDetails = []; // Initialiser une liste pour les détails des chambres
                reservation.chambres.forEach((chambreId: number) => {
                    this._shared.getChambreByIdReservation(chambreId).subscribe(
                        chambreData => {
                            reservation.chambresDetails.push(chambreData); // Ajouter les détails de la chambre
                            console.log(chambreData)
                        },
                        err => {
                            console.error('Erreur lors de la récupération de la chambre :', err);
                        }
                    );
                });
            });

            this.nombrereservations = this.reservations.length;
        },
        err => {
            console.log(err);
        }
    );

    this._shared.getHebergementByIdprop(this.idProp).subscribe(
        res => {
            this.hebergement = res;
        },
        err => {
            console.log(err);
        }
    );
}

delete(id:any){
    this._shared.refuserReservarion(id).subscribe(
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

    this._shared.accepterReservarion(id).subscribe(
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
