import { IPublisher, NewPublisher } from './publisher.model';

export const sampleWithRequiredData: IPublisher = {
  id: 65979,
  name: 'turquoise Guinea Rustic',
};

export const sampleWithPartialData: IPublisher = {
  id: 5096,
  name: 'Books calculate',
};

export const sampleWithFullData: IPublisher = {
  id: 91642,
  name: 'Card',
};

export const sampleWithNewData: NewPublisher = {
  name: 'green Grocery',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
