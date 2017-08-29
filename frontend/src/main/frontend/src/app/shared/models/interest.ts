export interface Interest {
  id: number,
  name: string,
  childInterests?: Array<Interest>
}
