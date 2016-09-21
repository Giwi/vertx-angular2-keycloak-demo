import { Component } from '@angular/core';
import { DemoService } from './demo.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  books : any[];

  constructor(private demoService : DemoService) {
    demoService.listBooks().subscribe(list => {
      this.books = list;
    }, err => {
      console.log("Error retrieving books. Is the server running, and is Keycloak configured correctly?", err.message);
    })
  }
}
