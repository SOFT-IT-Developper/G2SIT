import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Historiques } from './historiques.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class HistoriquesService {

    private resourceUrl = 'api/historiques';
    private resourceSearchUrl = 'api/_search/historiques';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(historiques: Historiques): Observable<Historiques> {
        const copy = this.convert(historiques);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(historiques: Historiques): Observable<Historiques> {
        const copy = this.convert(historiques);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Historiques> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.date = this.dateUtils
            .convertDateTimeFromServer(entity.date);
    }

    private convert(historiques: Historiques): Historiques {
        const copy: Historiques = Object.assign({}, historiques);

        copy.date = this.dateUtils.toDate(historiques.date);
        return copy;
    }

    findByDate(req: any): Observable<ResponseWrapper> {
        const params: URLSearchParams = new URLSearchParams();
        params.set('fromDate', req.fromDate);
        params.set('toDate', req.toDate);
        params.set('page', req.page);
        params.set('size', req.size);
        params.set('sort', req.sort);

        const options = {
            search: params
        };
        console.log(options)
        return this.http.get(`api/historiqueBetwenToDate`, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findByDateAndProduit(req: any): Observable<ResponseWrapper> {
        const params: URLSearchParams = new URLSearchParams();
        params.set('fromDate', req.fromDate);
        params.set('toDate', req.toDate);
        params.set('produitId', req.produitId);
        params.set('page', req.page);
        params.set('size', req.size);
        params.set('sort', req.sort);

        const options = {
            search: params
        };
        console.log(options)
        return this.http.get(`api/historiqueBetwenToDateProduit`, options)
            .map((res: Response) => this.convertResponse(res));
    }
}
