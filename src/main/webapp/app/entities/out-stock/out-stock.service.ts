import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { OutStock } from './out-stock.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OutStockService {

    private resourceUrl = 'api/out-stocks';
    private resourceSearchUrl = 'api/_search/out-stocks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(outStock: OutStock): Observable<OutStock> {
        const copy = this.convert(outStock);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(outStock: OutStock): Observable<OutStock> {
        const copy = this.convert(outStock);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<OutStock> {
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
        entity.datesortir = this.dateUtils
            .convertDateTimeFromServer(entity.datesortir);
    }

    private convert(outStock: OutStock): OutStock {
        const copy: OutStock = Object.assign({}, outStock);

        copy.datesortir = this.dateUtils.toDate(outStock.datesortir);
        return copy;
    }
    findByDate(req: any): Observable<Response> {
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
        return this.http.get(`api/outbydate`, options)
            .map((res: Response) => res);
    }
}
