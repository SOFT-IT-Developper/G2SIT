import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Operant } from './operant.model';
import { OperantService } from './operant.service';

@Component({
    selector: 'jhi-operant-detail',
    templateUrl: './operant-detail.component.html'
})
export class OperantDetailComponent implements OnInit, OnDestroy {

    operant: Operant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private operantService: OperantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOperants();
    }

    load(id) {
        this.operantService.find(id).subscribe((operant) => {
            this.operant = operant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOperants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'operantListModification',
            (response) => this.load(this.operant.id)
        );
    }
}
