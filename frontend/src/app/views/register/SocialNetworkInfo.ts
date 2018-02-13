export class SocialNetworkInfo {
    constructor(public socialNetwork: string,
                public userName: string,
                public userEmail: string,
                public userToken: string,
                public userId: string,
                public userImageURL: string,
                public expiresIn: string,
                public signedRequest: string
    ) {}
}
