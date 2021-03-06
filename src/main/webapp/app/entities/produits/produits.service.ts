import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Produits } from './produits.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProduitsService {

    private resourceUrl = 'api/produits';
    private resourceSearchUrl = 'api/_search/produits';

    constructor(private http: Http) { }

    create(produits: Produits): Observable<Produits> {
        const copy = this.convert(produits);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(produits: Produits): Observable<Produits> {
        const copy = this.convert(produits);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Produits> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(produits: Produits): Produits {
        const copy: Produits = Object.assign({}, produits);
        return copy;
    }
    // custome
  cont() {
      return this.http.get(`${this.resourceUrl}/countAll`).map((res) => res.json());
    }

}
