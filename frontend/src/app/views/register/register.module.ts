import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegisterComponent } from './register.component';
import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '/register',
    component: RegisterComponent,
    data: {
      title: 'Register'
    }
  }
];
@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [ RegisterComponent ]
})
export class RegisterModule { }
