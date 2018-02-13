export class MarkerMap {
  constructor(public lat: number,
              public lng: number,
              public label: string,
              public iconUrl: string,
              public draggable: boolean,
              public reserved: boolean) {
  }
}
