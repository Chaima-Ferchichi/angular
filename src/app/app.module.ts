import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminDashComponent } from './admin-dash/admin-dash.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { PropDashComponent } from './prop-dash/prop-dash.component';
import { SignupComponent } from './signup/signup.component';
import { HotelDetailsComponent } from './hotel-details/hotel-details.component';
import { ChambreComponent } from './chambre/chambre.component';
import { AjoutChambreComponent } from './ajout-chambre/ajout-chambre.component';
import { EditChambreComponent } from './edit-chambre/edit-chambre.component';
import { ListeReservationsComponent } from './liste-reservations/liste-reservations.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminDashComponent,
    HomeComponent,
    LoginComponent,
    PropDashComponent,
    SignupComponent,
    HotelDetailsComponent,
    ChambreComponent,
    AjoutChambreComponent,
    EditChambreComponent,
    ListeReservationsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
