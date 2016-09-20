import { Component } from '@angular/core';
import { DemoService } from './demo.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';

  constructor(private demoService : DemoService) {
console.log("getting books")
    demoService.listBooks().subscribe(list => {
      console.log(list)
    },
        err => {
      console.log(err.message)
        })
  }
}
