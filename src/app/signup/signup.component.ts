import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  alertMessage: string | null = null;
  registreForm: FormGroup;

  constructor(
    public _shared: SharedService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.registreForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.registreForm.valid) {
      this.alertMessage = 'Votre compte a été créé avec succès !';
      setTimeout(() => {
        this.alertMessage = null;
      }, 5000);
    } else {
      this.alertMessage = 'Veuillez remplir tous les champs correctement.';
    }
  }
}
