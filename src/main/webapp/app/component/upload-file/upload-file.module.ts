import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UploadFileComponent} from './upload-file.component';
import {FileUploadModule} from 'ng2-file-upload';
import {UPLOAD_ROUTE} from './upload-file.route';
import {RouterModule} from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
      FileUploadModule,
      RouterModule.forRoot([ UPLOAD_ROUTE ], { useHash: true }),
  ],
  declarations: [UploadFileComponent],
    exports: [UploadFileComponent]
})
export class UploadFileModule { }
