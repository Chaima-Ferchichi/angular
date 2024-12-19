import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-prop-dash',
  templateUrl: './prop-dash.component.html',
  styleUrl: './prop-dash.component.css',
  encapsulation: ViewEncapsulation.None
})
export class PropDashComponent implements OnInit {
  registreForm: FormGroup;



  demande={
    nom:"",
    localisation:"",
    telephone:"",
    fax:"",
    etoile:"",
    type:"",
    email:"",
    description:"",
    entertaiment:false,
    pescine:false,
    parking:false,
    restauration:false,
    serviceChambre:false,
    wifi:false,
    prix:0.0
  };

  prop:any;
  images: File[] = []; // Stocker les fichiers d'images sélectionnés

  constructor (public _shared:SharedService,private router:Router,private formBuilder: FormBuilder){
    this.registreForm = this.formBuilder.group({
      nom: ['', Validators.required],
      localisation: ['', Validators.required],
      fax: ['', Validators.required ],
      etoile: ['', Validators.required],
      type: ['', Validators.required],
      email: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')]],
      description: ['', Validators.required],
      entertaiment: ['', Validators.required],
      pescine: ['', Validators.required],
      parking: ['', Validators.required],
      restauration: ['', Validators.required],
      serviceChambre: ['', Validators.required],
      wifi: ['', Validators.required],
      prix: ['', Validators.required]
    });
  }
  ngOnInit(): void {
    this._shared.getHebergement().subscribe(
      res=>{
        console.log(res);
      },
      err=>{
        console.log(err);
      }
     );
  }

  onFileChange(event: any): void {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
      this.images.push(files[i]);
    }
  }

  ajouter(registreForm:any){

    console.log(registreForm.form);
    console.log('valeurs',JSON.stringify(registreForm.value));

    this.prop=3;

    if (registreForm.valid) {
      const formData = new FormData();
      // Ajouter les données de l'hébergement
      formData.append('hebergement', new Blob([JSON.stringify(this.demande)], { type: 'application/json' }));
       // Ajouter les images
      this.images.forEach((file) => {
        formData.append('images', file);
      });
     // Envoyer la requête au backend
     this._shared.createNewDemandeWithImages(formData, this.prop).subscribe(
     (res) => {
      console.log('Demande ajoutée avec succès', res);
      this.router.navigate(['/admin']);
     },
     (err) => {
      console.error('Erreur lors de l ajout', err);
    }
  );
    }
  }

}
