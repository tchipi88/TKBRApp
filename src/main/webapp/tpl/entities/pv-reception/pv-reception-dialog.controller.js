(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('PvReceptionDialogController', PvReceptionDialogController);

    PvReceptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'PvReception', 'Commande', 'Employe'];

    function PvReceptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, PvReception, Commande, Employe) {
        var vm = this;

        vm.pvReception = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.commandes = Commande.query({filter: 'pvreception-is-null'});
        $q.all([vm.pvReception.$promise, vm.commandes.$promise]).then(function() {
            if (!vm.pvReception.commande || !vm.pvReception.commande.id) {
                return $q.reject();
            }
            return Commande.get({id : vm.pvReception.commande.id}).$promise;
        }).then(function(commande) {
            vm.commandes.push(commande);
        });
        vm.employes = Employe.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pvReception.id !== null) {
                PvReception.update(vm.pvReception, onSaveSuccess, onSaveError);
            } else {
                PvReception.save(vm.pvReception, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:pvReceptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateReception = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
