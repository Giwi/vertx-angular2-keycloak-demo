import { AuthHttp } from 'angular2-jwt';
import {Injectable} from "@angular/core";
import { Observable } from 'rxjs'
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';

@Injectable()
export class DemoService {
    constructor(private authHttp: AuthHttp) {}

    listBooks() : Observable<any> {

        console.log("listing books")
        return this.authHttp.get("http://localhost:8000/api/books").do(res => console.log(res)).map(res => res.json())
    }

}