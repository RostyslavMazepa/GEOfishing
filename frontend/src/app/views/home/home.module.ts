import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { AgmCoreModule } from '@agm/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HomeRoutingModule,
    AgmCoreModule.forRoot({
        apiKey: 'AIzaSyD12SCHQuKvio_rOlz0Opgxo21Jd3xP1do'
    })
  ],
  declarations: [ HomeComponent ]
})
export class HomeModule { }
