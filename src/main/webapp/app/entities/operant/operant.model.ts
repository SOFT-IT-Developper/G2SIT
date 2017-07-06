import { BaseEntity } from './../../shared';

export class Operant implements BaseEntity {
    constructor(
        public id?: number,
        public fistname?: string,
        public lastname?: string,
        public telephone?: number,
        public stocks?: BaseEntity[],
        public stockes?: BaseEntity[],
    ) {
    }
}
