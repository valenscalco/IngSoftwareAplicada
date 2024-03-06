import { IAuthor, NewAuthor } from './author.model';

export const sampleWithRequiredData: IAuthor = {
  id: 82416,
  firstName: 'Mathias',
  lastName: 'Smith',
};

export const sampleWithPartialData: IAuthor = {
  id: 61290,
  firstName: 'Price',
  lastName: 'Morar',
};

export const sampleWithFullData: IAuthor = {
  id: 57525,
  firstName: 'Dawson',
  lastName: 'Jenkins',
};

export const sampleWithNewData: NewAuthor = {
  firstName: 'Ramona',
  lastName: 'Corkery',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
