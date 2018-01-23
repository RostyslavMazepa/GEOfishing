import { Component, OnInit } from '@angular/core';

import { DicFishType } from './DicFishType';
import { HomeService } from './home.service';
import { DicFishKind } from './DicFishKind';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  templateUrl: 'home.component.html',
    providers: [ HomeService ]
})
export class HomeComponent implements OnInit {

    // Component properties
    statusCode: number;
    processValidation = false;
    /*---------------------- Google Map ----------------------*/

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

    mapClicked($event: any) {
        // вбиваємо останній не збережений маркер
        if (this.markers[this.markers.length - 1].reserved == false) {
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
            fishingNameFormControlInput: this.fishingPlaceForm.get('fishingNameFormControlInput').value.trim(),
            fishingLongitudeFormControlInput: $event.coords.lat,
            fishingLatitudeFormControlInput: $event.coords.lng,
            fishingCommentsFormControlTextarea: this.fishingPlaceForm.get('fishingCommentsFormControlTextarea').value.trim()
        })
    }

    clickedMarker(label: string, index: number) {
        console.log(`clicked the marker: ${label || index}`);
    }

    markerDragEnd(marker: marker, $event: any) {
        console.log('dragEnd', marker, $event);
        // відображаємо координати в формі
        this.fishingPlaceForm.setValue({
            fishingNameFormControlInput: this.fishingPlaceForm.get('fishingNameFormControlInput').value.trim(),
            fishingLongitudeFormControlInput: $event.coords.lat,
            fishingLatitudeFormControlInput: $event.coords.lng,
            fishingCommentsFormControlTextarea: this.fishingPlaceForm.get('fishingCommentsFormControlTextarea').value.trim()
        })
    }

/*---------------------- Fishing Place Form ----------------------*/
    // Create form
    fishingPlaceForm = new FormGroup({
        fishingNameFormControlInput: new FormControl('', Validators.required),
        fishingLongitudeFormControlInput: new FormControl('', Validators.required),
        fishingLatitudeFormControlInput: new FormControl('', Validators.required),
        fishingCommentsFormControlTextarea: new FormControl('', Validators.required)
    });

    constructor(private fishingPlaceService: HomeService) { }

    ngOnInit(): void {
        this.getAllFishTypes();
        this.getAllFishKinds();
    }

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
        const fishTypeKindValue = this.fishingForm.get('fishKindFormControlSelect').value.trim();
        console.log(fishTypeKindValue);
        const onSelf = this.allFishKinds;
        let dicFishTypes;
        for (let i = 0; i < onSelf.length; ++i) {
            if (onSelf[i].fishKindId == fishTypeKindValue){
                dicFishTypes = onSelf[i].dicFishTypeSet;
                console.log(dicFishTypes);
            }
        }
        this.allFishTypes = dicFishTypes;
    }

    /*---------------------- Fishing Form ----------------------*/

    allFishTypes: DicFishType[];
    allFishKinds: DicFishKind[];

    // Create form
    fishingForm = new FormGroup({
        fishKindFormControlSelect: new FormControl('', Validators.required),
        fishTypeFormControlSelect: new FormControl('', Validators.required),
    });

}

interface marker {
    lat: number;
    lng: number;
    label?: string;
    draggable: boolean;
    reserved: boolean;
};
