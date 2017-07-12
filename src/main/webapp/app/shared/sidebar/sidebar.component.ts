import { Component } from '@angular/core';
import {Principal} from "../auth/principal.service";


@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
    isActive = false;
    showMenu = '';
    constructor( private principal: Principal ){

    }
    eventCalled() {
        this.isActive = !this.isActive;
    }
    addExpandClass(element: any) {
        if (element === this.showMenu) {
            this.showMenu = '0';
        } else {
            this.showMenu = element;
        }
    }
    isAuthenticated() {
        return this.principal.isAuthenticated();
    }
}
