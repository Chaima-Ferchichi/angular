import { Component,OnInit,ViewEncapsulation } from '@angular/core';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-chambre',
  templateUrl: './chambre.component.html',
  styleUrl: './chambre.component.css',
  encapsulation: ViewEncapsulation.None
})
export class ChambreComponent implements OnInit{

  chambres:any;
  nombreDemandes: number = 0;

  constructor(private sharedService: SharedService) {}

  ngOnInit(): void {
    
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
  delete(id:any){
    this.sharedService.deleteChambre(id).subscribe(
      res=>{
          console.log(res);
          this.ngOnInit();
      },
      err=>{
        console.log(err);
      }
    );
  }


}
