import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashComponent } from './admin-dash/admin-dash.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PropDashComponent } from './prop-dash/prop-dash.component';
import { SignupComponent } from './signup/signup.component';
import { HotelDetailsComponent } from './hotel-details/hotel-details.component';
import { ChambreComponent } from './chambre/chambre.component';
import { AjoutChambreComponent } from './ajout-chambre/ajout-chambre.component';
import { EditChambreComponent } from './edit-chambre/edit-chambre.component';
import { ListeReservationsComponent } from './liste-reservations/liste-reservations.component';
const routes: Routes = [
{path:'',redirectTo:'/home',pathMatch:'full'},
{path:'login',component:LoginComponent},
{path:'home',component:HomeComponent},
{path:'admin',component:AdminDashComponent},
{path:'prop',component:PropDashComponent},
{path:'signup',component:SignupComponent},
{path: 'hotel-details/:id', component: HotelDetailsComponent },
{path: 'editChambre/:id', component: EditChambreComponent },
{path:'listeChambres',component:ChambreComponent},
{path:'ajoutChambre',component:AjoutChambreComponent},
{path:'reservation-liste',component:ListeReservationsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
