import { BaseEntity } from './../../shared';

const enum Etats {
    'BON',
    'REPARATION',
    'GATER'
}

export class Produits implements BaseEntity {
    constructor(
        public id?: number,
        public nameProduit?: string,
        public reference?: string,
        public emplacement?: string,
        public description?: string,
        public captureContentType?: string,
        public capture?: any,
        public etat?: Etats,
        public stocks?: BaseEntity[],
        public stock?: BaseEntity,
        public categorie?: BaseEntity,
    ) {
    }
}
