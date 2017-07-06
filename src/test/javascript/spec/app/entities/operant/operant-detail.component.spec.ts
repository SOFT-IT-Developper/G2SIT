import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { G2SitTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OperantDetailComponent } from '../../../../../../main/webapp/app/entities/operant/operant-detail.component';
import { OperantService } from '../../../../../../main/webapp/app/entities/operant/operant.service';
import { Operant } from '../../../../../../main/webapp/app/entities/operant/operant.model';

describe('Component Tests', () => {

    describe('Operant Management Detail Component', () => {
        let comp: OperantDetailComponent;
        let fixture: ComponentFixture<OperantDetailComponent>;
        let service: OperantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [G2SitTestModule],
                declarations: [OperantDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OperantService,
                    JhiEventManager
                ]
            }).overrideTemplate(OperantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperantService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Operant(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.operant).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
