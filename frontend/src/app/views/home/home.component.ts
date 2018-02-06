import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { DicFishType } from './DicFishType';
import { HomeService } from './home.service';
import { DicFishKind } from './DicFishKind';
import { AppService } from '../../app.service';

@Component({
  templateUrl: 'home.component.html',
  providers: [ HomeService ]
})
export class HomeComponent implements OnInit {

    // Component properties
    statusCode: number;
    processValidation = false;

    // initial center position for the map
    // title = 'Київ';
    lat = 50.431782;
    lng = 30.516382;

    /**
     * while(markers) {
     * this.lat = 51.431782;
     * this.lng = 31.516382;
     * console.log('Work while' + this.lat + ' ' + this.lng);
     * };
     */

    // google maps zoom level
    zoom = 10;

    markers: marker[] = [
        {
            lat: 50.552707419200665,
            lng: 30.513153076171875,
            label: ':)',
            draggable: false,
            reserved: true
        },
        {
            lat: 50.4330171113566,
            lng: 30.574951171875,
            label: ':|',
            draggable: false,
            reserved: true
        },
        {
            lat: 50.333572,
            lng: 30.616350,
            label: ':(',
            draggable: false,
            reserved: true
        }
    ];

  // Create form fishingPlaceForm
  fishingPlaceForm = new FormGroup({
    fishingNameInput: new FormControl('', Validators.required),
    fishingLongitudeInput: new FormControl('', Validators.required),
    fishingLatitudeInput: new FormControl('', Validators.required),
    fishingCommentsTextarea: new FormControl('', Validators.required)
  });

  /*---------------------- Fishing Form ----------------------*/

  allFishTypes: DicFishType[];
  allFishKinds: DicFishKind[];

  // Create form fishingForm
  fishingForm = new FormGroup({
    fishKindSelect: new FormControl('', Validators.required),
    fishTypeSelect: new FormControl('', Validators.required),
  });

  constructor(private fishingPlaceService: HomeService, private appService: AppService) { }

  ngOnInit(): void {
    this.getAllFishTypes();
    this.getAllFishKinds();
    this.appService.checkCredentials();
  }
/*
  logout(){
    this.appService.logout();
  }
*/
  /*---------------------- Google Map ----------------------*/
    mapClicked($event: any) {
        // вбиваємо останній не збережений маркер
        if (this.markers[this.markers.length - 1].reserved === false) {
            this.markers.pop();
        }
        // добавляємо маркер при натисканні на карті
        this.markers.push({
            lat: $event.coords.lat,
            lng: $event.coords.lng,
            draggable: true,
            reserved: false
        });
        // відображаємо координати в формі
        // console.log($event.coords.lat + ' ' + $event.coords.lng);
        this.fishingPlaceForm.setValue({
            fishingNameInput: this.fishingPlaceForm.get('fishingNameInput').value.trim(),
            fishingLongitudeInput: $event.coords.lat,
            fishingLatitudeInput: $event.coords.lng,
            fishingCommentsTextarea: this.fishingPlaceForm.get('fishingCommentsTextarea').value.trim()
        })
    }

    clickedMarker(label: string, index: number) {
        console.log(`clicked the marker: ${label || index}`);
    }

    markerDragEnd( marker: marker, $event: any ) {
        console.log('dragEnd', marker, $event);
        // відображаємо координати в формі
        this.fishingPlaceForm.setValue({
            fishingNameInput: this.fishingPlaceForm.get('fishingNameInput').value.trim(),
            fishingLongitudeInput: $event.coords.lat,
            fishingLatitudeInput: $event.coords.lng,
            fishingCommentsTextarea: this.fishingPlaceForm.get('fishingCommentsTextarea').value.trim()
        })
    }

/*---------------------- Fishing Place Form ----------------------*/

    onFishingPlaceFormSubmit() {
        this.processValidation = true;
        if (this.fishingPlaceForm.invalid) {
            return; // Validation failed, exit from method.
        }
    }

    // Fetch all
    getAllFishTypes() {
        this.fishingPlaceService.getAllDicFishTypes()
            .subscribe(
                data => this.allFishTypes = data,
                errorCode =>  this.statusCode = errorCode);
    }

    // Fetch all
    getAllFishKinds() {
        this.fishingPlaceService.getAllDicFishKinds()
            .subscribe(
                data => this.allFishKinds = data,
                errorCode =>  this.statusCode = errorCode);
    }

    getAllFishTypeByKindId() {
        const fishTypeKindValue = this.fishingForm.get('fishKindSelect').value.trim();
        console.log(fishTypeKindValue);
        const onSelf = this.allFishKinds;
        let dicFishTypes;
        for (let i = 0; i < onSelf.length; ++i) {
            if (onSelf[i].fishKindId === fishTypeKindValue){
                dicFishTypes = onSelf[i].dicFishTypeSet;
                console.log(dicFishTypes);
            }
        }
        this.allFishTypes = dicFishTypes;
    }
}

interface marker {
    lat: number;
    lng: number;
    label?: string;
    draggable: boolean;
    reserved: boolean;
};
