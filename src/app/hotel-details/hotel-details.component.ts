import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-hotel-details',
  templateUrl: './hotel-details.component.html',
  styleUrls: ['./hotel-details.component.css']
})
export class HotelDetailsComponent implements OnInit {

  hebergement: any; // Un seul objet hébergement
  id:any;
  hebergementImages: { [key: string]: any[] } = {};

  chambres:any;
  nombreDemandes: number = 0; 

  chambresDisponibles: any[] = [];

  hebergementId!: number; // Dynamic ID from route parameter
  errorMessage: string = '';

  selectedChambres: number[] = []; // Store selected chambre IDs

  // Reservation form data
  reservationData = {
    clientData : {
      email: "nawresouerhani3@gmail.com",
      name: "nawres",
      phone: "52807115"
    },
    date_arrivee: '',
    date_depart: '',
    adultes: 0,
    enfants: 0,
    montant_total:100,
    status:'En Attente'
  };
  constructor(
    private route: ActivatedRoute,
    private sharedService: SharedService
  ) {}

ngOnInit(): void {

  this.route.params.subscribe((params) => {
    this.hebergementId = +params['id']; // Extract ID from route
    this.fetchChambresDisponibles();
  });
  // Récupérer l'id depuis les paramètres de la route
  this.id = this.route.snapshot.paramMap.get('id');

  // Charger les détails de l'hébergement
  this.sharedService.getHebergementById(this.id).subscribe({
    next: (data) => {
      this.hebergement = data; // Assigner l'objet unique
      this.loadImagesForHebergement(); // Charger les images pour cet hébergement
    },
    error: (err) => {
      console.error('Erreur lors de la récupération des détails de l\'hébergement:', err);
    }
  });


  this.sharedService.getAllChambres().subscribe(
    res=>{
      console.log(res);
      this.chambres=res;
      this.nombreDemandes = this.chambres.length;  // Assigner le nombre de demandes
      
    },
    err=>{
      console.log(err);
    }
   );
   
}

loadImagesForHebergement(): void {
  this.sharedService.getHebergementImages(this.id).subscribe({
    next: (images) => {
      this.hebergementImages[this.id] = images;
      console.log(`Images pour l'hébergement ${this.id}:`, images); // Debug
    },
    error: (err) => {
      console.error(`Erreur lors du chargement des images pour l'hébergement ${this.id}:`, err);
    },
  });
}
getFirstImage(): string {
  return this.hebergementImages[this.id]?.[0]
    ? 'data:image/jpeg;base64,' + this.hebergementImages[this.id][0]
    : 'assets/default-image.jpg'; // Chemin vers une image par défaut
}



fetchChambresDisponibles(): void {
  this.sharedService.getChambresDisponiblesByHebergement(this.hebergementId).subscribe(
    (data) => {
      console.log('Chambres disponibles:', data); // Debug
      this.chambresDisponibles = data || [];
    },
    (error) => {
      console.error('Erreur lors de la récupération des chambres disponibles:', error);
    }
  );
  
}


   // Handle checkbox change
   onChambreSelect(chambreId: number, event: any): void {
    if (event.target.checked) {
      this.selectedChambres.push(chambreId);
    } else {
      this.selectedChambres = this.selectedChambres.filter(id => id !== chambreId);
    }
  }

  // Submit reservation form
 
  // Submit reservation form
  submitReservation(): void {
    if (this.selectedChambres.length > 0) {
      const reservation = {
        ...this.reservationData,
       chambreIds: this.selectedChambres,
      };
  
      this.sharedService.createReservation(this.id, reservation).subscribe({
        next: (response) => {
          console.log('Reservation created successfully:', response);
          alert('Reservation created successfully!');
          console.log(this.id)
          console.log(this.reservationData)

        },
        error: (error) => {
          console.error('Error creating reservation:', error);
          console.log(this.id)
          console.log(this.reservationData)
          if (error.status === 500) {
            alert(`Error creating reservation: ${error.message}`);
          }
          if (error.error) {
            console.error('Backend error message:', error.error);
            alert(`Backend error: ${error.error}`);
          }
        }
      });
    } else {
      alert('Please select at least one room.');
    }
    this.ngOnInit();
  }
  
}