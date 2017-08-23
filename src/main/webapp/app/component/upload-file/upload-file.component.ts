///<reference path="../../../../../../node_modules/@angular/core/src/metadata/lifecycle_hooks.d.ts"/>
import { Component, OnInit } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import {AuthServerProvider} from '../../shared/auth/auth-jwt.service';
    const URL = '/api/upload';
@Component({
  selector: 'jhi-upload-file',
  templateUrl: './upload-file.component.html',
  styles: []
})
export class UploadFileComponent implements OnInit {

    public uploader: FileUploader = new FileUploader({url: URL, authToken : `Bearer ${this.acces.getToken()} ` });
    public hasBaseDropZoneOver = false;
    public hasAnotherDropZoneOver = false;
    constructor(private acces: AuthServerProvider) {
        console.log(this.acces.getToken());
    }
    public fileOverBase(e: any): void {
        this.hasBaseDropZoneOver = e;
    }

    public fileOverAnother(e: any): void {
        this.hasAnotherDropZoneOver = e;
    }
    ngOnInit(): void {
    }
}
