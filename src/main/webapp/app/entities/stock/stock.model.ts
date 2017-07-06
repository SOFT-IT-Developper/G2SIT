import { BaseEntity } from './../../shared';

export class Stock implements BaseEntity {
    constructor(
        public id?: number,
        public quantite?: number,
        public description?: string,
        public dateentrer?: any,
        public retour?: boolean,
        public comment?: string,
        public produit?: BaseEntity,
        public operantst?: BaseEntity,
    ) {
        this.retour = false;
    }
}
