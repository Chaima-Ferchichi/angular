import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { port } from '../env';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  constructor(private http: HttpClient) {}

  createNewDemande(demande: any, id: any): Observable<Object> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${port}/Demande/Hebergement/${id}`, demande, { headers });
  }

  getHebergement(): Observable<any> {
    return this.http.get(`${port}/Demande/fullHebergementNonAccepter`);
  }

  accepterDemande(id: any): Observable<any> {
    return this.http.get(`${port}/Demande/accepterHebergement/${id}`);
  }

  refuserDemande(id: any): Observable<any> {
    return this.http.delete(`${port}/Demande/refuserHebergement/${id}`);
  }

  createNewDemandeWithImages(data: FormData, id: any): Observable<Object> {
    return this.http.post(`${port}/Demande/HebergementWithImages/${id}`, data);
  }

  getHebergementsDisponibles(): Observable<any[]> {
    return this.http.get<any[]>(`${port}/Demande/hebergementsDisponibles`);
  }

  getHebergementById(id: number): Observable<any> {
    return this.http.get<any>(`${port}/Demande/hebergementById/${id}`);
  }

  getHebergementImages(id: number): Observable<string[]> {
    return this.http.get<string[]>(`${port}/Demande/hebergement/${id}/images`);
  }
  getAllChambres(): Observable<any> {
    return this.http.get(`${port}/Demande/fullChambres`);
  }
  deleteChambre(id: any): Observable<any> {
    return this.http.delete(`${port}/Demande/Chambre/${id}`);
  }

  AjouterChambre(chambre: any, id: any): Observable<Object> {
    return this.http.post(`${port}/Demande/Chambre/${id}`, chambre);
  }
  getChambreById(id: any): Observable<any> {
    return this.http.get<any>(`${port}/Demande/ChambreById/${id}`);
  }
  updateChambreById( id: any,chambre: any): Observable<any> {
    return this.http.put<any>(`${port}/Demande/Chambre/${id}`,chambre);
  }
  getReservationByProprietaireId(id: any): Observable<any> {
    return this.http.get<any>(`${port}/Demande/Reservation/${id}`);
  }
  getHebergementByIdprop(id: any): Observable<any> {
    return this.http.get<any>(`${port}/Demande/Hebergement/${id}`);
  }
  getClientbyid(id: any): Observable<any> {
    return this.http.get<any>(`${port}/Demande/ClientById/${id}`);
  }
  getChambreByIdReservation(chambreId: number): Observable<any> {
    return this.http.get<any>(`${port}/Demande/ChambreByIdreservation/${chambreId}`);
  }

  accepterReservarion(id: any): Observable<any> {
    return this.http.get(`${port}/Demande/accepterReservation/${id}`);
  }

  refuserReservarion(id: any): Observable<any> {
    return this.http.delete(`${port}/Demande/refuserReservation/${id}`);
  }
// Method to create a reservation (POST request)
createReservation(id: number, reservationData: any): Observable<any> {
  const apiUrl = 'http://localhost:8088/api/reservations'; // URL inside the function
  const url = `${apiUrl}/${id}`;
  return this.http.post(url, reservationData);
}
getChambresDisponiblesByHebergement(hebergementId: number): Observable<any[]> {
  return this.http.get<any[]>(`${port}/Demande/ListeDesChambresDisponibles/${hebergementId}`);
}

}
