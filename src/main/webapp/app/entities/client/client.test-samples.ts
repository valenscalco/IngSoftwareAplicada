import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 71655,
  firstName: 'Esta',
  lastName: 'Stamm',
};

export const sampleWithPartialData: IClient = {
  id: 81014,
  firstName: 'Antonia',
  lastName: 'Kovacek',
  phone: '981.379.2056 x899',
};

export const sampleWithFullData: IClient = {
  id: 26444,
  firstName: 'Javier',
  lastName: 'Brakus',
  email: 'Jaylen44@gmail.com',
  address: 'Human card',
  phone: '872-250-7474',
};

export const sampleWithNewData: NewClient = {
  firstName: 'Cleveland',
  lastName: 'Wiza',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
