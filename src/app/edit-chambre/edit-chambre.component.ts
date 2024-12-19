import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-edit-chambre',
  templateUrl: './edit-chambre.component.html',
  styleUrl: './edit-chambre.component.css',
  encapsulation: ViewEncapsulation.None
})
export class EditChambreComponent implements OnInit{



  chambre:any;

  id:any;


  prop:any;
  

  constructor (private act:ActivatedRoute,public _shared:SharedService,private router:Router,private formBuilder: FormBuilder){
  
  }
  ngOnInit(): void {
    this.id = this.act.snapshot.paramMap.get('id');

    this._shared.getChambreById(this.id).subscribe(
      res => {
        console.log(res);
        this.chambre = res;
      },
      err => {
        console.log(err);
      }
    );
  }
  update(updateForm:any) {

    console.log(updateForm.form);
    console.log('valeurs',JSON.stringify(updateForm.value));

      // Appel à votre service pour mettre à jour le conseiller d'apprentissage
      this._shared.updateChambreById(this.id, this.chambre).subscribe(
        res => {
          console.log(res);
          this.router.navigate(['/listeChambres']);
        },
        err => {
          console.log(err);
        }
      );
    
  }
}
