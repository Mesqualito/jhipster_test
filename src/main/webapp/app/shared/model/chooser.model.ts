import { Enums } from 'app/shared/model/enumerations/enums.model';

export interface IChooser {
  id?: number;
  enumeration?: Enums;
}

export class Chooser implements IChooser {
  constructor(public id?: number, public enumeration?: Enums) {}
}
