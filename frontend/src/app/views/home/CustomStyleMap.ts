export const CustomStyleMap =
  [
    {
      featureType: 'all',
      stylers: [
        {saturation: -80}
      ]
    }, {
    featureType: 'road.arterial',
    elementType: 'geometry',
    stylers: [
      {hue: '#00ffee'},
      {saturation: 50}
    ]
  }, {
    featureType: 'poi.business',
    elementType: 'labels',
    stylers: [
      {visibility: 'off'}
    ]
  }, {
    featureType: 'water',
    elementType: 'geometry',
    stylers: [
      {color: '#04b4ff'}
    ]
  },
    {
      featureType: 'landscape.natural',
      stylers: [
        {lightness: -60},
        {gamma: 2.75},
        {saturation: -57},
        {hue: '#061bff'}
      ]
    }
  ];
