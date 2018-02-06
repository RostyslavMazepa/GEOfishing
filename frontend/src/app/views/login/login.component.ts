import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AppService } from '../../app.service';

@Component({
  templateUrl: 'login.component.html'
})
export class LoginComponent {

  // Create form
  loginForm = new FormGroup({
    userName: new FormControl('', Validators.required),
    userPassword: new FormControl('', Validators.required)
  });

  constructor(private service: AppService) {}

  login() {
    this.service.obtainAccessToken(
      this.loginForm.get('userName').value.trim(),
      this.loginForm.get('userPassword').value.trim()
    );
  }
}
