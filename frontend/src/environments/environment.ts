// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.

export const environment = {
  production: false
};

const host = 'localhost';
const port = '8080';

/** Authentication Facebook */
export const appIdFacebook = '149318269057115';

/** Authentication Google Map */
export const apiKeyGoogleMap = 'AIzaSyD12SCHQuKvio_rOlz0Opgxo21Jd3xP1do';

/** Authentication Google */
export const elementIdGoogle = 'googleBtn';
export const appIdGoogle = '834859447878-vj9upm5llmp4asg6ben3b5n4tnjs3d4v.apps.googleusercontent.com';

/** Authentication BackEnd */
export const urlToken = 'http://' + host + ':' + port + '/oauth/token';
export const clientId = 'geofappid';
export const clientSecret = 'mXunCZdhCD8tRA7a';

/** URLs for CRUD operations Dictionary BackEnd */
export const dicFishesUrl = 'http://' + host + ':' + port + '/fishes/';
export const dicFishesTypesUrl = 'http://' + host + ':' + port + '/fishes/types/';

/** URLs for POST operation Auth BackEnd */
export const urlSocialAuth = 'http://' + host + ':' + port + '/oauth/socialAuth/';
export const urlRegisterAuth = 'http://' + host + ':' + port + '/registration';

/** Authentication https://home.openweathermap.org */
export const appIdOpenWeatherMap = '289dee5d308891597dc6f37fe40cbfa6';
