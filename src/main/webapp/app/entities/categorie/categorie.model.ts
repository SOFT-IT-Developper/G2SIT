import { BaseEntity } from './../../shared';

export class Categorie implements BaseEntity {
    constructor(
        public id?: number,
        public nameCategorie?: string,
        public fournisseur?: string,
        public description?: string,
        public produits?: BaseEntity[],
    ) {
    }
}
