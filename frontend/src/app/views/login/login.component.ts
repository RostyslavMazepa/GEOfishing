import {Component, OnInit} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AppService } from '../../app.service';
import { Cookie } from 'ng2-cookies';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {

  processValidation = false;

  // Create form
  loginForm = new FormGroup({
    userName: new FormControl('', Validators.required),
    userPassword: new FormControl('', Validators.required)
  });

  constructor(private service: AppService, private router: Router) {}

  ngOnInit(): void {
    if (Cookie.check('access_token')) {
      this.router.navigate(['/home']);
    }
  }

  login() {
    this.processValidation = true;
    if (this.loginForm.invalid) {
      return; // Validation failed, exit from method.
    }

    this.service.obtainAccessToken(
      this.loginForm.get('userName').value.trim(),
      this.loginForm.get('userPassword').value.trim()
    );
  }
}
