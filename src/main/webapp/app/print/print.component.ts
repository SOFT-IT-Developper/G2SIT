import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation,
    Input, Output, OnChanges, ElementRef,
    EventEmitter } from '@angular/core';
@Component({
  selector: 'jhi-print',
    encapsulation: ViewEncapsulation.None,
    template: `
     <button (click)="printDiv()" 
        class="btn btn-sm btn-primary">Print</button>
    
    `,
    styles: [`
    @media screen {
        #printpage, #printpage * {
            display:none;
        }
    }
      @media print {
          body *{
              visibility:hidden;
              background: transparent;
            -webkit-print-color-adjust: exact !important; 
          }
          
          #printpage, #printpage *{
              visibility:visible;
               -webkit-print-color-adjust: exact !important; 
                box-shadow: inset 0 0 0 1000px gold;
          }

          #printpage{
            position:absolute;
            left:0;
            top:0;             
          }
      }
    `]
})
export class PrintComponent implements OnChanges {

    @Input('section') section: string;
    @Output() sectionChange = new EventEmitter<any>();

    constructor(private ele: ElementRef){
        if (this.section === undefined)
            this.section = '';
    }

    ngOnChanges(changes){
        if (changes.section && changes.section.currentValue !== undefined
            && changes.section.currentValue !== ''){

        }
    }

    afterPrint(){
        console.log('after print');
        this.ele.nativeElement.children[0].innerHTML = '';
        this.sectionChange.emit('');
        this.section = '';

    }

    printDiv() {
            console.log('arrive');
        if (this.section && this.section != undefined && this.section != '') {
            const printContents = document.getElementById(this.section).innerHTML;
            const originalContents = document.body.innerHTML;

            if (window) {
                if (navigator.userAgent.toLowerCase().indexOf('chrome') > -1) {
                    const popup = window.open('', '_blank',
                        'width=600,height=600,scrollbars=no,menubar=no,toolbar=no,'
                        + 'location=no,status=no,titlebar=no');

                    popup.window.focus();
                    popup.document.write('<!DOCTYPE html><html><head>  '
                        + '<link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.css" '
                        + 'media="screen,print">'
                        + '<link rel="stylesheet" href="style.css" media="screen,print">'
                        +    '</head><body onload="window.print()"><div class="reward-body">'
                        + printContents + '</div></html>');
                    popup.onbeforeunload = function(event) {
                        popup.close();
                        return '.\n';
                    };
                    popup.onabort = function(event) {
                        popup.document.close();
                        popup.close();
                    };
                } else {
                    const popup = window.open('', '_blank', 'width=800,height=600');
                    popup.document.open();
                    popup.document.write('<html><head>' +
                        +'<link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.css"'
                        + ' media="all">'
                        + '<link rel="stylesheet" href="style.css" media="all">'
                        + '</head><body onload="window.print();window.close()">' + printContents + '</html>');
                    popup.document.close();
                }

               //popup.document.close();
            }
           // this.fetchStylesheets();
            return true;
        }
    }

    fetchStylesheets() {
        let output = '';
        for (let i = 0; i < document.styleSheets.length; i++){
            output = output + ' <link rel="stylesheet" type="text/css" href="' +
                window.document.styleSheets[0].href + '" /> ';
        }
        return output;
    }

    fetchscriptSheets(){
        let output = '';
        for (let i = 0; i < document.scripts.length; i++) {
            output = output + ' <script type="text/javascript" src="' +
                window.document.scripts[0].src + '" /> ';
        }
        return output;
    }
}
