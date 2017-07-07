import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Stock } from './stock.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class StockService {

    private resourceUrl = 'api/stocks';
    private resourceSearchUrl = 'api/_search/stocks';
    private resourceUrlcustom = 'produitid';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(stock: Stock): Observable<Stock> {
        const copy = this.convert(stock);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(stock: Stock): Observable<Stock> {
        const copy = this.convert(stock);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Stock> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findByProduitId(id: number): Observable<Stock> {
        return this.http.get(`${this.resourceUrl}/${this.resourceUrlcustom}/${id}`).map((res: Response) => {
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
        entity.dateentrer = this.dateUtils
            .convertDateTimeFromServer(entity.dateentrer);
    }

    private convert(stock: Stock): Stock {
        const copy: Stock = Object.assign({}, stock);

        copy.dateentrer = this.dateUtils.toDate(stock.dateentrer);
        return copy;
    }
}
