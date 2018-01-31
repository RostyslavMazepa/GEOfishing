import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { AgmCoreModule } from '@agm/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { apiKeyGoogleMap } from '../../../environments/environment';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HomeRoutingModule,
    AgmCoreModule.forRoot({
        apiKey: apiKeyGoogleMap
    })
  ],
  declarations: [ HomeComponent ]
})
export class HomeModule { }
