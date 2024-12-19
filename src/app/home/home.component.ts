import { Component, OnInit } from '@angular/core';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  hebergements: any[] = [];
  filteredHebergements: any[] = [];
  searchLocation: string = '';
  hebergementImages: { [key: number]: string[] } = {}; // Images par ID d'hébergement

  constructor(private sharedService: SharedService) {}

  ngOnInit(): void {
    this.loadHebergementsDisponibles();
  }

  loadHebergementsDisponibles(): void {
    this.sharedService.getHebergementsDisponibles().subscribe({
      next: (data) => {
        console.log('Hébergements disponibles:', data); // Debug
        this.hebergements = data;
        this.filteredHebergements = data; // Initialisation des hébergements filtrés
        this.loadImagesForHebergements(); // Charger les images après avoir récupéré les hébergements
      },
      error: (err) => {
        console.error('Erreur lors du chargement des hébergements:', err);
      },
    });
  }

  loadImagesForHebergements(): void {
    this.hebergements.forEach((hebergement) => {
      this.sharedService.getHebergementImages(hebergement.id).subscribe({
        next: (images) => {
          this.hebergementImages[hebergement.id] = images;
          console.log(`Images pour hébergement ${hebergement.id}:`, images); // Debug
        },
        error: (err) => {
          console.error(`Erreur lors du chargement des images pour l'hébergement ${hebergement.id}:`, err);
        },
      });
    });
  }

  getFirstImage(id: number): string {
    return this.hebergementImages[id]?.[0]
      ? 'data:image/jpeg;base64,' + this.hebergementImages[id][0]
      : 'assets/default-image.jpg'; // Chemin vers une image par défaut
  }

  onSearch(): void {
    if (this.searchLocation) {
      this.filteredHebergements = this.hebergements.filter((hebergement) =>
        hebergement.localisation.toLowerCase().includes(this.searchLocation.toLowerCase())
      );
    } else {
      this.filteredHebergements = this.hebergements;
    }
  }
}
