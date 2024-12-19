import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-ajout-chambre',
  templateUrl: './ajout-chambre.component.html',
  styleUrl: './ajout-chambre.component.css',
  encapsulation: ViewEncapsulation.None
})
export class AjoutChambreComponent implements OnInit {

  registreForm: FormGroup;



  chambre={
    bed:"",
    nbPersonne: 0,
    espace: 0.0,
    climatisation:false,
    balcon:false,
    televesion:false,
    salledebains:false,
    wifi:false,
    prix:0.0
  };

  prop:any;
  

  constructor (public _shared:SharedService,private router:Router,private formBuilder: FormBuilder){
    this.registreForm = this.formBuilder.group({
      bed: ['', Validators.required],
      nbPersonne: ['', Validators.required],
      espace: ['', Validators.required ],
      prix: ['', Validators.required],
      climatisation: ['', Validators.required],
      balcon: ['', Validators.required],
      televesion: ['', Validators.required],
      salledebains: ['', Validators.required],
      wifi: ['', Validators.required]
    });
  }
  ngOnInit(): void {
    this._shared.getAllChambres().subscribe(
      res=>{
        console.log(res);
      },
      err=>{
        console.log(err);
      }
     );
  }

  ajouter(registreForm:any){

    console.log(registreForm.form);
    console.log('valeurs',JSON.stringify(registreForm.value));
    
    this.prop=3
    this._shared.AjouterChambre(this.chambre,this.prop).subscribe(
      res=>{
      console.log(res);
      this.chambre={
        bed:"",
        nbPersonne: 0,
        espace: 0.0,
        climatisation:false,
        balcon:false,
        televesion:false,
        salledebains:false,
        wifi:false,
        prix:0.0
      };
    
      this.ngOnInit();
     this.router.navigate(['/listeChambres']);
      },
      err=>{
       console.log(err);
      }
     );
  }
}
