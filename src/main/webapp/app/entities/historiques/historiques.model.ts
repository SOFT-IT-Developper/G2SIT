import { BaseEntity, User } from './../../shared';

export class Historiques implements BaseEntity {
    constructor(
        public id?: number,
        public operation?: string,
        public date?: any,
        public user?: User,
        public stocks?: BaseEntity,
        public outs?: BaseEntity,
    ) {
    }
}
