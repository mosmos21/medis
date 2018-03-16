import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthService } from '../services/auth.service';

@Injectable()
export class UploadFileService {

  constructor(
    private http: HttpClient,
    @Inject('hostname') private hostname: string,
    private authService: AuthService,
  ) { }

  pushFileToStorage(file: File, http: HttpClient) {
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    http.post(this.hostname + "settings/me/icon", formdata, { withCredentials: true, headers: this.authService.headerMultipart(), responseType: 'text'}).subscribe(
      id => {
      },
      error => {
      }
    );
  }

  getFiles(): Observable<Object>{
    return this.http.get('/getallfiles')
  }

}
