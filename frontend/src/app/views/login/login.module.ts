import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginComponent } from './login.component';
import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '/login',
    component: LoginComponent,
    data: {
      title: 'Login'
    }
  }
];
@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ LoginComponent ]
})
export class LoginModule { }
