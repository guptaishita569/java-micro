import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { DemoAngularMaterailModule } from './DemoAngularMaterialModule';
import { UserStorageService } from './services/stoarge/user-storage.service';
import { CommonModule } from '@angular/common';
import { NavigationEnd} from '@angular/router';
 
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink,
    DemoAngularMaterailModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ecomweb';
  isCustomerLoggedIn : boolean = UserStorageService.isCustomerLoggedIn();
  isAdminLoggedIn : boolean = UserStorageService.isAdminLoggedIn();
 
  currentRoute: string = '';
  constructor(private router: Router) { }
 
  ngOnInit(): void {
    this.router.events.subscribe(event => {
      this.isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn();
      this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
      this.router.events.subscribe((event) => {
        if (event instanceof NavigationEnd) {
          this.currentRoute = event.url;
        }
      });
    })
  }
 
  shouldDisplayContent(): boolean {
    return !this.isCustomerLoggedIn && !this.isAdminLoggedIn && !(this.currentRoute === '/login' || this.currentRoute === '/signup');
  }
  logout() {
    UserStorageService.signOut();
    this.router.navigateByUrl('login');
  }
}