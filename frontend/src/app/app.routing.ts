import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import { FullLayoutComponent, SimpleLayoutComponent } from './containers';
import { LoginComponent } from './views/login/login.component';
import { RegisterComponent } from './views/register/register.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, data: { title: 'Login' }},
  { path: 'register', component: RegisterComponent, data: { title: 'Register' }},
  { path: '', component: FullLayoutComponent, data: { title: 'Home' },
    children: [
      { path: 'home', loadChildren: './views/home/home.module#HomeModule' },
      { path: 'dashboard', loadChildren: './views/dashboard/dashboard.module#DashboardModule' },
      { path: 'components', loadChildren: './views/components/components.module#ComponentsModule' },
      { path: 'icons', loadChildren: './views/icons/icons.module#IconsModule' },
      { path: 'widgets', loadChildren: './views/widgets/widgets.module#WidgetsModule' },
      { path: 'charts', loadChildren: './views/chartjs/chartjs.module#ChartJSModule' }
    ]
  },
  { path: 'pages', component: SimpleLayoutComponent, data: { title: 'Pages'},
    children: [
      { path: '', loadChildren: './views/pages/pages.module#PagesModule' }
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
