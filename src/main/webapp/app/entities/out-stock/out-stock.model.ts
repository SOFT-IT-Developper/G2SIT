import { BaseEntity } from './../../shared';

export class OutStock implements BaseEntity {
    constructor(
        public id?: number,
        public quantite?: number,
        public datesortir?: any,
        public projet?: string,
        public client?: string,
        public cause?: string,
        public operantos?: BaseEntity,
        public produit?: BaseEntity,
    ) {
    }
}
