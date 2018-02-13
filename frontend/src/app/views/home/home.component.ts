import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

import {HomeService} from './home.service';
import {AppService} from '../../app.service';

import {DicFishes} from './DicFishes';
import {DicFishesTypes} from './DicFishesTypes';

import {MarkerMap} from './MarkerMap';
import {CustomStyleMap} from './CustomStyleMap';

@Component({
  templateUrl: 'home.component.html',
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  customStyleMap = CustomStyleMap;

  // Component properties
  statusCode: number;
  processValidation = false;

  // initial center position for the map
  // title = 'Київ';

  centerPosition = {
    lat: 50.431048863295935,
    lng: 30.589885711669922
  };

  // google maps zoom level
  zoom = 11;

  // markerMap: MarkerMap[];
  markersMap: MarkerMap[] = [
    {
      lat: 50.552707419200665,
      lng: 30.513153076171875,
      label: ':)',
      iconUrl: '../../../assets/img/iconmap/marker-icon-red.png',
      draggable: false,
      reserved: true
    },
    {
      lat: 50.4330171113566,
      lng: 30.574951171875,
      label: ':|',
      iconUrl: '../../../assets/img/iconmap/marker-icon-red.png',
      draggable: false,
      reserved: true
    },
    {
      lat: 50.333572,
      lng: 30.616350,
      label: ':(',
      iconUrl: '../../../assets/img/iconmap/marker-icon-red.png',
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

  /**---------------------- Fishing Form ----------------------*/

  dicFishes: DicFishes[];
  dicFishesTypes: DicFishesTypes[];

  // Create form fishingForm
  fishingForm = new FormGroup({
    fishesSelect: new FormControl('', Validators.required),
    fishesTypesSelect: new FormControl('', Validators.required),
  });

  constructor(private homeService: HomeService, private appService: AppService) {
  }

  ngOnInit(): void {
    this.appService.checkCredentials();
    this.getDicFishes();
    this.getDicFishesTypes();
    this.getGeolocation();
    // console.log(this.dicFishes)
    // console.log(this.dicFishesTypes)
  }

  /**---------------------- Google Map ----------------------*/

  // центрування карти по координатам браузера
  getGeolocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.centerPosition.lat = position.coords.latitude;
          this.centerPosition.lng = position.coords.longitude;
          console.log(this.centerPosition)
        },
        (error) => {
          console.log('Geolocation error - ' + error);
        })
    } else {
      console.log('Geolocation not working !!!')
    }
  }

  mapClicked($event: any) {
    // вбиваємо останній не збережений маркер
    if (this.markersMap[this.markersMap.length - 1].reserved === false) {
      this.markersMap.pop();
    }
    // добавляємо маркер при натисканні на карті
    this.markersMap.push(<MarkerMap>{
      lat: $event.coords.lat,
      lng: $event.coords.lng,
      label: '+',
      iconUrl: '../../../assets/img/iconmap/marker-icon-green.png',
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

  markerDragEnd(markerMap, $event: any) {
    console.log('dragEnd', markerMap, $event);
    // відображаємо координати в формі
    this.fishingPlaceForm.setValue({
      fishingNameInput: this.fishingPlaceForm.get('fishingNameInput').value.trim(),
      fishingLongitudeInput: $event.coords.lat,
      fishingLatitudeInput: $event.coords.lng,
      fishingCommentsTextarea: this.fishingPlaceForm.get('fishingCommentsTextarea').value.trim()
    })
  }

  /**---------------------- Fishing Place Form ----------------------*/

  onFishingPlaceFormSubmit() {
    this.processValidation = true;
    if (this.fishingPlaceForm.invalid) {
      return; // Validation failed, exit from method.
    }
  }

  // Fetch all
  getDicFishes() {
    this.homeService.getDicFishes()
      .subscribe(
        data => this.dicFishes = data,
        errorCode => this.statusCode = errorCode);
  }

  // Fetch all
  getDicFishesTypes() {
    this.homeService.getDicFishesTypes()
      .subscribe(
        data => this.dicFishesTypes = data,
        errorCode => this.statusCode = errorCode);
  }
/*
  getAllFishTypeByKindId() {
    const fishTypeKindValue = this.fishingForm.get('fishKindSelect').value.trim();
    console.log(fishTypeKindValue);
    const onSelf = this.allFishKinds;
    let dicFishTypes;
    for (let i = 0; i < onSelf.length; ++i) {
      if (onSelf[i].fishKindId === fishTypeKindValue) {
        dicFishTypes = onSelf[i].dicFishTypeSet;
        console.log(dicFishTypes);
      }
    }
    this.allFishTypes = dicFishTypes;
  }
*/
}
